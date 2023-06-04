import { Link } from 'react-router-dom';
import logo from '../icons/logo.png';
import useSignUp from './signUp';
import useLogin from './login';
import { useContext, useState } from 'react';
import { GlobalContext } from '../ClobalContext';
import account from "../icons/user.png";
import down from "../icons/down-arrow.png";
import up from "../icons/up-arrow.png";

function NavBar() {

    const [SignUp, signUpCtrl] = useSignUp();
    const [Login, loginCtrl] = useLogin();
    const { isAuthenticated } = useContext(GlobalContext);

    return (
        <>
            <nav className='fixed top-0 left-0 w-full bg-white' >
                <div className='flex w-11/12 m-auto' >
                    <Link className='ml-16 py-6 relative' to={'/'} >
                        <div className='w-16 absolute right-full bottom-0' >
                            <img className='w-full'
                                src={logo}
                                alt=''
                            />
                        </div>
                        <p>
                            e-Sbitar
                        </p>
                    </Link>
                    <div className='w-8/12' >
                        <ul className='flex w-10/12 m-auto' >
                            <CustomLink content='Home' to='/' />
                            <CustomLink content='About us' to='/' />
                            <CustomLink content='Conacte us' to='/' />
                            <CustomLink content='FAQ' to='/' />
                            <CustomLink content='reviews' to='/' />
                        </ul>
                    </div>
                    <div className='grow' />
                    {!isAuthenticated ? <ul className='flex gap-4 py-6' >
                        <li>
                            <button
                                className='p-2 bg-c-blue-800 text-white hover:border-c-blue-800
                                    hover:bg-white hover:text-c-blue-800 border
                                '
                                onClick={signUpCtrl.open}
                            >
                                sign up
                            </button>
                        </li>
                        <li>
                            <button
                                className='text-c-blue-800 border border-c-blue-800 p-2
                                    transition-background-color hover:bg-c-blue-800
                                    hover:text-white
                                '
                                onClick={loginCtrl.open}
                            >
                                sign in
                            </button>
                        </li>
                    </ul> : <Account />}
                </div>
                <SignUp />
                <Login />
            </nav>
            <div className="mb-20" />
        </>
    )
}


function Account() {

    const [isOpen,setIsOpen] = useState(false);

    const signOut = () => {
        localStorage.removeItem('token');
        window.location.reload();
    }

    const {user} = useContext(GlobalContext);

    return (
        <div className='flex gap-1 items-center relative' >
            <div className='w-8 aspect-square' >
                <img className='w-full aspect-square'
                    src={
                        user.picture 
                            ? 'http://localhost:8080'+user.picture
                            : account}
                    alt=''
                />
            </div>
            <p className='mr-8' >{user.name}</p>
            <button className='w-8 p-2 aspect-square' 
                onClick={() => setIsOpen(prev => !prev)}
            >
                <img className='w-full aspect-square'
                    src={isOpen ? down : up}
                    alt=''
                />
            </button>
            {isOpen ? <div className='absolute top-full text-sm' >
                <ul className='block bg-blue-300' >
                    <li className='w-full inline-block py-2 pl-2 hover:bg-blue-400' >
                        <Link to={'/diseases'} >
                            diseases and symptoms
                        </Link>
                    </li>
                    <li className='w-full inline-block py-2 pl-2 hover:bg-blue-400' >
                        <Link>
                            articles
                        </Link>
                    </li>
                    <li className='w-full inline-block py-2 pl-2 hover:bg-blue-400' >
                        <Link>
                            medications
                        </Link>
                    </li>
                    <li className='w-full inline-block py-2 pl-2 hover:bg-blue-400' >
                        <Link>
                            medical consultations
                        </Link>
                    </li>
                    <li className='w-full inline-block py-2 pl-2 hover:bg-blue-400' >
                        <button onClick={signOut} >
                            sign out
                        </button>
                    </li>
                </ul> 
            </div> : null }
        </div>

    )
}

function CustomLink({ content, to }) {
    return (
        <li className='hover:bg-c-blue-600 py-6 text-center hover:text-white w-full' >
            <Link to={to}>
                {content}
            </Link>
        </li>
    )
}

export default NavBar;