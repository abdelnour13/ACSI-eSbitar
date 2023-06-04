import { useCallback, useEffect, useState } from "react";
import usePopUp from "../utils/usePopUp";
import GoogleSignUp from "./googleSignUp";
import axios from "axios";

const url = 'http://localhost:8080';

function useLogin() {

    const [PopUp, ctrl] = usePopUp();

    const Element = () => {

        const [credentials, setCredentials] = useState({
            email: "",
            password: "",
        });

        const [validationError,setValidationError] = useState({
            attribute:'',
            message:''
        });

        const [type,setType] = useState('password');

        const handleInputChange = (ev) => {
            setCredentials((prev) => {
                return {
                    ...prev,
                    [ev.target.name]: ev.target.value,
                };
            });
        };

        const signUp = useCallback(async (ev) => {

            ev.preventDefault();

            try {
                const response = await axios.post(`${url}/auth/login`,{
                    email: credentials.email,
                    hashedPassword : credentials.password
                });
                localStorage.setItem('token', response.data.token);
                window.location.reload();
            } catch(err) {
                if(err.response.data.err) setValidationError(err.response.data.err);
                console.log(validationError);
            }
        }, [credentials,validationError]);

        useEffect(() => {
            if(validationError.message) {
                alert(validationError.message)
            }
        },[validationError])

        return <PopUp>
            <div className="p-4">
                <form className="bg-transparent border-0">
                    <h2 className="text-2xl font-semibold text-c-blue-950 mb-4">
                        Sign In
                    </h2>
                    <div className="flex flex-col gap-3 mb-4">
                        <input
                            className="w-full outline-0 p-2 border border-gray-500"
                            placeholder="email"
                            name="email"
                            value={credentials.email}
                            onChange={handleInputChange}
                            type="email"
                        />
                        <div>
                            <input
                                className="w-full outline-0 p-2 border border-gray-500"
                                placeholder="password"
                                name="password"
                                value={credentials.password}
                                onChange={handleInputChange}
                                type={type}
                            />
                            <div className="flex gap-1 mr-0 ml-auto max-w-fit" >
                                <label>show password</label>
                                <input type="checkbox" 
                                    onChange={(ev) => {
                                        setType(type === 'password' ? 'text' : 'password')
                                    }}
                                />
                            </div>
                        </div>
                    </div>
                    <div className="w-full m-auto">
                        <button className="m-auto w-full block bg-c-blue-950 py-3
                         text-white hover:bg-c-blue-700 text-xl font-semibold"
                         onClick={signUp}
                        >
                            sign in
                        </button>
                        <div className="w-full flex gap-1 items-center my-2" >
                            <div className="h-[1px] bg-black grow" />
                            <p>or</p>
                            <div className="h-[1px] bg-black grow" />
                        </div>
                        <GoogleSignUp />
                    </div>
                </form>
            </div>
        </PopUp>
    };

    return [Element, ctrl];
}

export default useLogin;