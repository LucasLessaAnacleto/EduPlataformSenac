export default function Button({ children, ...props }: React.ButtonHTMLAttributes<HTMLButtonElement>) {
    return (       
        <button
            {...props}
            className={`bg-blue-600 hover:bg-blue-500 text-white text-sm font-medium px-4 py-2 rounded-lg transition shadow-lg shadow-blue-600/20 cursor-pointer
            ${props.className}`}
        >
            {children}
        </button>
    )
}