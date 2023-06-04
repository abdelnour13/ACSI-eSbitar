import { Outlet } from "react-router-dom";
import SideBar from "../components/sideBar";


function Admin() {
    return (
        <div className="flex relative h-full" >
            <div className="absolute left-0 tobottom-0 w-60 h-full" >
                <SideBar />
            </div>
            <main className="grow ml-60 bg-gray-100" >
                <Outlet />
            </main>
        </div>
    )
}

export default Admin;