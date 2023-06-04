import { useRef, useState } from "react";
import usePopUp from "../utils/usePopUp";
import diseaseApi from "../apis/diseaseApi";

function useAddDisease() {

    const [PopUp,ctrl] = usePopUp();

    const Element = ({ symptoms,onAdd }) => {

        const [symptomsLookUp,setSymptomsLookUp] = useState(() => {
            return symptoms.reduce((prev, curr) => {
                prev[curr.symptom_id] = false;
                return prev;
            }, {});
        });

        const [name,setName] = useState('');

        const add = (ev) => {
            ev.preventDefault();
            const disease = {
                name,
                symptoms: Object.keys(symptomsLookUp)
                    .filter(key => symptomsLookUp[key])
                    .map(v => parseInt(v))
            };
            ctrl.close();
            diseaseApi.createDisease(disease)
                .then(o => {
                    onAdd(o[0])
                });
            // window.location.reload();
        }

        return <PopUp rootClassName="rounded" >
            <div className="h-64 flex" >
                <ul className="overflow-y-scroll p-2" >
                    {
                        symptoms.map(symptom => {
                            return (
                                <li key={symptom.symptom_id} className="flex justify-between gap-2 mb-2" >
                                    <div>
                                        {symptom.name}
                                    </div>
                                    <Switch value={symptomsLookUp[symptom.symptom_id]} 
                                        onChange={(ev) => {
                                            setSymptomsLookUp(prev => {
                                                return {
                                                    ...prev,
                                                    [symptom.symptom_id]:!prev[symptom.symptom_id]
                                                }
                                            })
                                        }}
                                    />
                                </li>
                            )
                        })
                    }
                </ul>
                <div className="w-72 h-full" >
                    <form className="w-11/12 m-auto h-full relative mt-4" >
                        <div>
                            <label className="font-semibold text-primary-100 block" >
                                Name
                            </label>
                            <input className="outline-0 p-2 border-red-300 border w-full"
                                placeholder="type the disease's name"
                                value={name}
                                onChange={(ev) => setName(ev.target.value)}
                            />
                        </div>
                        <div className="flex justify-between bottom-6 absolute w-full" >
                            <button className="p-2 rounded bg-gray-200" >
                                cancel
                            </button>
                            <button className="p-2 rounded bg-green-500 text-white" 
                                onClick={add}
                            >
                                add
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </PopUp>
    }

    return [Element,ctrl];

}

function Switch({ value,onChange }) {

    const circleRef = useRef(null);
    const buttonRef = useRef(null);

    /*useEffect(() => {
        circleRef.current.classList.toggle("translate-x-full");
        circleRef.current.classList.toggle("translate-x-0");
        buttonRef.current.classList.toggle("bg-gray-400");
        buttonRef.current.classList.toggle("bg-c-blue-950");
        console.log(value)
    }, [value]);*/

    return (
        <div className={
            "w-10 rounded-full relative p-1 "+(!value?'bg-gray-400':'bg-c-blue-950')
        }
            ref={buttonRef}
        >
            <div className={`w-6/12 aspect-square bg-white rounded-full 
                transition-transform `+(value?'translate-x-full':'translate-x-0')}
                ref={circleRef}
            />
            <input 
                type="checkbox" 
                className="absolute top-0 opacity-0 cursor-pointer w-full aspect-square z-50" 
                onChange={onChange} 
                checked={value}
            />
        </div>
    )
}

export default useAddDisease;