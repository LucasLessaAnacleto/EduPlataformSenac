package com.senac.ApiEduPlataformSenac.services;

import com.senac.ApiEduPlataformSenac.model.dto.ModuloRequest;
import com.senac.ApiEduPlataformSenac.model.dto.ModuloResponse;
import com.senac.ApiEduPlataformSenac.model.entities.Curso;
import com.senac.ApiEduPlataformSenac.model.entities.Modulo;
import com.senac.ApiEduPlataformSenac.model.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private CursoService cursoService;

    public List<ModuloResponse> listarPorCurso(Long cursoId, Authentication authentication) throws Exception {
        cursoService.buscarCursoDoProfessor(cursoId, authentication);

        return moduloRepository.findAllByCursoId(cursoId)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public Long salvar(ModuloRequest request, Authentication authentication) throws Exception {
        if (request.cursoId() == null) {
            throw new Exception("CURSO_ID_OBRIGATORIO");
        }

        Curso curso = cursoService.buscarCursoDoProfessor(request.cursoId(), authentication);

        Modulo modulo = new Modulo();
        modulo.setTitulo(request.titulo());
        modulo.setOrdem(request.ordem());
        modulo.setCurso(curso);

        return moduloRepository.save(modulo).getId();
    }

    public ModuloResponse atualizar(Long id, ModuloRequest request, Authentication authentication) throws Exception {
        Modulo moduloBanco = buscarModulo(id);

        if (moduloBanco == null) {
            throw new Exception("MODULO_NAO_ENCONTRADO");
        }

        cursoService.buscarCursoDoProfessor(moduloBanco.getCurso().getId(), authentication);

        moduloBanco.setTitulo(request.titulo());
        moduloBanco.setOrdem(request.ordem());

        moduloRepository.save(moduloBanco);

        return converterParaResponse(moduloBanco);
    }

    public void deletar(Long id, Authentication authentication) throws Exception {
        Modulo modulo = buscarModulo(id);

        if (modulo == null) {
            throw new Exception("MODULO_NAO_ENCONTRADO");
        }

        cursoService.buscarCursoDoProfessor(modulo.getCurso().getId(), authentication);

        moduloRepository.delete(modulo);
    }

    private Modulo buscarModulo(Long id) {
        return moduloRepository.findById(id).orElse(null);
    }

    private ModuloResponse converterParaResponse(Modulo modulo) {
        return new ModuloResponse(
                modulo.getId(),
                modulo.getTitulo(),
                modulo.getOrdem(),
                modulo.getCurso().getId()
        );
    }
}
