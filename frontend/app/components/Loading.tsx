type Props = {
    message?: string
    fullScreen?: boolean
}

export default function Loading({
    message,
    fullScreen = false
}: Props) {

    return (
        <div className={`
            flex flex-col items-center justify-center gap-4
            ${fullScreen ? "min-h-screen" : "py-4"}
        `}>

            {/* <div className="
                w-10 h-10
                border-2 border-zinc-700
                border-t-blue-500
                rounded-full
                animate-spin
            " /> */}
            <div className="relative w-10 h-10">
                <div className="
                    absolute inset-0
                    rounded-full
                    border-2 border-zinc-800
                " />
                <div className="
                    absolute inset-0
                    rounded-full
                    border-2 border-transparent
                    border-t-blue-500
                    animate-spin
                " />
            </div>
            <p className="text-sm text-zinc-400">
                {message || ""}
            </p>

        </div>
    )
}