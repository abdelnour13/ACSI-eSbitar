import { useLoaderData } from "react-router-dom";
import SearchBar from "../components/searchBar";
import Disease from "../components/disease";
import diseasesApi from "../apis/diseaseApi";
import { useState } from "react";

function Diseases() {

    const [diseases,setDiseases] = useState(useLoaderData().diseases);
    const handleSearchValueChange = async (value) => {
        const [diseases,] = await diseasesApi.getDiseases({
            search: value
        });
        setDiseases(diseases);
    }

    return (
        <>
            <SearchBar 
                onSerachChanged={handleSearchValueChange}
            />
            <div className="w-11/12 m-auto mt-2 overflow-y-auto" >
                <h1 className="text-2xl" >
                    Diseases
                </h1>
                <div className="grid grid-cols-3 gap-4" >
                    {diseases.map(disease => {
                        return <Disease {...disease} />
                    })}
                </div>
            </div>
        </>
    )
}

export default Diseases;