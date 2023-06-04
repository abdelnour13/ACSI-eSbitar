import { Link } from "react-router-dom";
import ic_disease from "../icons/disease.png";
import ic_account from "../icons/management.png";
import ic_med from "../icons/medicine.png";
import ic_signout from "../icons/log-out.png";
import ic_article from "../icons/articles.png";
import ic_reviews from "../icons/rating.png";
import ic_pay from "../icons/card.png";
import { useContext } from "react";
import { GlobalContext } from "../ClobalContext";

function SideBar() {

    const {user} = useContext(GlobalContext);

    return (
        <aside className="bg-[#050542] h-full text-white text-sm" >
            <div className="h-full flex flex-col" >
                <div className="pl-4 py-4 border-b border-red-500 flex gap-2 items-center" >
                    <div className="w-14 aspect-square rounded-full" >
                        <img className="w-full aspect-square rounded-full"
                            alt=""
                            src={'http://localhost:8080'+user.picture}
                        />
                    </div>
                    <div className="w-full" >
                        <div>{user.name}</div>
                        <div className="text-green-500" >admin</div>
                    </div>
                </div>
                <h2 className="pl-4 py-4 text-xl font-semibold border-b border-red-500" >
                    Admin Panel
                </h2>
                <ul className="grow flex flex-col" >
                    <ListItem name='accounts' logo={ic_account} />
                    <ListItem name='diseases & symptoms' logo={ic_disease} to={'/admin/diseases-symptoms'} />
                    <ListItem name='medications' logo={ic_med} />
                    <ListItem name='articles' logo={ic_article} />
                    <ListItem name='reviews' logo={ic_reviews} />
                    <ListItem name='payement operations' logo={ic_pay} />
                    <div className="grow" />
                    <li className="hover:bg-[#0a0a5e] inline-block" >
                        <button className="w-full flex gap-2 pl-4 py-4" >
                            <div className="w-6 aspect-square" >
                                <img className="w-full aspect-square"
                                    alt=""
                                    src={ic_signout}
                                />
                            </div>
                            <span>sign out</span>
                        </button>
                    </li>
                </ul>
            </div>
        </aside>
    )
}

function ListItem({ name, logo, to }) {
    return (
        <li className="hover:bg-[#0a0a5e] inline-block" >
            <Link className="pl-4 py-4 flex items-center gap-2" to={to} >
                <div className="w-6 aspect-square" >
                    <img className="w-full aspect-square"
                        alt=""
                        src={logo}
                    />
                </div>
                <p>{name}</p>
            </Link>
        </li>
    )
}

export default SideBar;