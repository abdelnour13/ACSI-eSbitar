function Disease({ name,symptoms }) {
    return (
        <div className="border border-c-blue-950 p-4" >
            <h2 className="text-c-blue-950 font-bold ">{name}</h2>
            <h2 className="font-semibold ">symptoms</h2>
            <ul className="flex gap-2 flex-wrap" >
                {symptoms.map(symptom => {
                    return (
                        <li className="py-1 px-2 rounded-full border border-green-800 bg-green-200" >
                            {symptom.name}
                        </li>
                    )
                })}
            </ul>
        </div>
    )
}

export default Disease;