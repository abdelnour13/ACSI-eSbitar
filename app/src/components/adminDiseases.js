import { useEffect, useState } from "react";
import { useLoaderData } from "react-router-dom";
import useConfirmPopUp from "../utils/useConfirmPopUp";
import ic_search from "../icons/search.png";
import ic_edit from "../icons/edit.png";
import ic_delete from "../icons/delete.png";
import symptomApi from "../apis/symptomApi";
import diseaseApi from "../apis/diseaseApi";
import useAddDisease from "./addDisease";
import ic_add from "../icons/add-button.png";

function AdminDiseases() {

  const [diseases, setDiseases] = useState(useLoaderData().diseases);
  const [DeleteDiseaseConfPopUp, deleteDiseaseConfPopUpCtrl] =
    useConfirmPopUp();
  const [selectedDisease, setSelectedDisease] = useState(-1);
  const [symptoms, setSymptoms] = useState([]);
  const [AddDiseasePopUp,addDiseasePopUpCtrl] = useAddDisease();
  const [symptomName,setSymptomName] = useState('');
  const [search,setSearch] = useState('');

  useEffect(() => {
    (async () => {
      const [symptoms,] = await symptomApi.getAllSymptoms();
      console.log(symptoms)
      if (symptoms) setSymptoms(symptoms);
    })();
  }, []);

  const deleteDisease = async () => {
    setDiseases((prev) =>
      prev.filter((disease) => disease.disease_id !== selectedDisease)
    );
    await diseaseApi.deleteDisease(selectedDisease);
  };

  const addSymptom = async () => {
    const [s,] = await symptomApi.createSymptom({ name: symptomName });
    setSymptoms(prev => [...prev,{
      symptom_id: s,
      name: symptomName
    }]);
  }

  const deleteSymptom = async (id) => {
    setSymptoms(prev => prev.filter(s => s.symptom_id !== id));
    await symptomApi.deleteSymptom(id)
  }

  return (
    <div className="h-full flex gap-4 overflow-hidden">
      <div className="w-9/12" >
        <h1 className="text-3xl font-bold text-[#050542] m-4">Diseases</h1>
        <table className="table-fixed border-separate border-spacing-4 bg-white ml-4 rounded shadow-lg max-h-80">
          <thead className="border-b border-gray-500">
            <tr className="relative" >
              <th className="text-[#050542] font-semibold py-2">
                id
              </th>
              <th className="text-[#050542] font-semibold text-left">
                Name
              </th>
              <th className="text-[#050542] font-semibold text-left">
                Symptoms
              </th>
              <th className=""></th>
              <th className=""></th>
              <button className="p-2 rounded bg-[#50C878] text-white absolute right-3 hover:bg-[#60DD78]" 
                onClick={addDiseasePopUpCtrl.open}
              >
                add a disease
              </button>
            </tr>
          </thead>
          <tbody>
            {diseases.map((disease) => {
              return (
                <tr className="p-2" key={disease.disease_id}>
                  <td>{disease.disease_id}</td>
                  <td>{disease.name}</td>
                  <td className="flex flex-wrap gap-2 max-w-md text-sm">
                    {disease.symptoms.map((symptom) => {
                      return (
                        <span className="border border-green-500 bg-green-200 rounded-full p-2">
                          {symptom.name}
                        </span>
                      );
                    })}
                  </td>
                  <td>
                    <button className="p-2 bg-cyan-500 rounded text-white hover:bg-cyan-600">
                      edit
                    </button>
                  </td>
                  <td>
                    <button
                      className="p-2 bg-red-500 rounded text-white hover:bg-red-600"
                      onClick={() => {
                        deleteDiseaseConfPopUpCtrl.open();
                        setSelectedDisease(disease.disease_id);
                      }}
                    >
                      delete
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
        <AddDiseasePopUp symptoms={symptoms} onAdd={(d) => {
          setDiseases(prev => [...prev,d]);
        }}/>
        <DeleteDiseaseConfPopUp
          content={
            <p className="mb-4">
              are you sure you want to delete this disease ?
            </p>
          }
          onCancelClicked={deleteDiseaseConfPopUpCtrl.close}
          onOkClicked={() => {
            deleteDiseaseConfPopUpCtrl.close();
            deleteDisease();
          }}
        />
      </div>
      <div className="w-3/12 bg-white h-full border-l border-blue-950 p-2">
        <h1 className="text-xl font-bold text-[#050542] mb-2">Symptoms</h1>
        <div className="flex gap-2 mb-2">
          <input
            className="p-1 outline-0 border border-gray-300 w-full"
            placeholder="search..."
            value={search}
            onChange={(ev) => setSearch(ev.target.value)}
          />
          <div className="aspect-square w-8">
            <img src={ic_search} alt="" className="w-full aspect-square" />
          </div>
        </div>
        <div className="flex gap-2 items-center" >
          <input
            className="p-1 outline-0 border border-gray-300 w-full"
            placeholder="name"
            value={symptomName}
            onChange={(ev) => setSymptomName(ev.target.value)}
          />
          <button className="aspect-square w-8" onClick={addSymptom} >
            <img src={ic_add} alt="" className="w-full aspect-square" />
          </button>
        </div>
        <ul>
          {symptoms.filter(e => e.name.toUpperCase().includes(search.toUpperCase())).map((symptom) => {
            return (
              <li className="flex my-2 gap-2 rounded bg-gray-100 p-1" >
                <input className="outline-0 pl-2 w-full"
                    value={symptom.name} 
                    disabled={true}
                />
                <button className="aspect-square w-8 p-1 rounded border border-cyan-500">
                  <img
                    src={ic_edit}
                    alt=""
                    className="w-full aspect-square"
                  />
                </button>
                <button className="aspect-square w-8 p-1 rounded border border-red-500"
                  onClick={() => deleteSymptom(symptom.symptom_id)}
                >
                  <img
                    src={ic_delete}
                    alt=""
                    className="w-full aspect-square"
                  />
                </button>
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
}

export default AdminDiseases;