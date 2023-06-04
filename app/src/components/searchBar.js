import { useState } from "react";
import ic_search from "../icons/search.png";

function SearchBar({ onSerachChanged }) {

    const [search,setSearch] = useState('');

    return (
        <div className="flex w-11/12 m-auto gap-2" >
            <input className="p-2 grow outline-0 border border-c-blue-800"
                name="search"
                placeholder="search for a disease by name...."
                value={search}
                onChange={(ev) => {
                    setSearch(ev.target.value)
                    onSerachChanged(ev.target.value)
                }}
            />
            <button className="w-10 aspect-square" >
                <img className="aspect-square"
                    src={ic_search}
                    alt=""
                />
            </button>
        </div>
    )
}

export default SearchBar;