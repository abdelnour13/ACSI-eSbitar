import { Outlet } from "react-router-dom";
import NavBar from "./navBar";


function Screen() {

    

    return (
        <>
            <div className="h-full w-full" >
                <NavBar />
                <Outlet />
            </div>
        </>
    )
}

export default Screen;