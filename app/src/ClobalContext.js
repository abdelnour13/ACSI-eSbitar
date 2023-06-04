import { createContext } from "react";
import { Outlet, useLoaderData } from "react-router-dom";

export const GlobalContext = createContext(null);

function Context() {
    const {isAuthenticated,user} = useLoaderData();
    return (
        <GlobalContext.Provider value={{isAuthenticated,user}}>
            <Outlet />
        </GlobalContext.Provider>
    )
}

export default Context;