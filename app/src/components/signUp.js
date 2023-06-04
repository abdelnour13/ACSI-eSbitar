import { useCallback, useEffect, useRef, useState } from "react";
import usePopUp from "../utils/usePopUp";
import GoogleSignUp from "./googleSignUp";
import axios from "axios";

const url = 'http://localhost:8080';

function useSignUp() {

    const [PopUp, ctrl] = usePopUp();

    const pictureRef = useRef(null);

    const Element = () => {

        const [user, setUser] = useState({
            email: "",
            username: "",
            password: "",
            rePassword: "",
            gender: "male",
        });

        const [validationError,setValidationError] = useState({
            attribute:'',
            message:''
        });

        const handleInputChange = (ev) => {

            if(ev.target.type === 'file') {
                setUser((prev) => {
                    return {
                        ...prev,
                        photo: ev.target.files[0],
                    };
                });
            }

            setUser((prev) => {
                return {
                    ...prev,
                    [ev.target.name]: ev.target.value,
                };
            });
        };

        const signUp = useCallback(async (ev) => {

            ev.preventDefault();

            if(user.password !== user.rePassword) {
                setValidationError({
                    attribute: 'rePassword',
                    message: 'please choose a password that you can remember'
                });
                return;
            }

            const form = new FormData();
            if(pictureRef.current && pictureRef.current.files 
                && pictureRef.current.files[0]) {
                form.append('picture', pictureRef.current.files[0]);
            }
            form.append('email',user.email || '');
            form.append('name', user.username || '');
            form.append('hashedPassword', user.password || '');
            form.append('gender', user.gender || '');
            form.append('rePassword', user.rePassword || '');
    
            try {
                await axios.post(`${url}/auth/sign-up`,form, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });
                window.location.reload();
            } catch(err) {
                if(err.response.data.err) setValidationError(err.response.data.err);
                console.log(validationError);
            }
        }, [user,validationError]);

        useEffect(() => {
            if(validationError.message) {
                alert(validationError.message)
            }
        },[validationError])

        return <PopUp>
            <div className="p-4">
                <form className="bg-transparent border-0">
                    <h2 className="text-2xl font-semibold text-c-blue-950 mb-4">
                        Sign Up
                    </h2>
                    <div className="flex flex-col gap-3 mb-4">
                        <input
                            className="w-full outline-0 p-2 border border-gray-500"
                            placeholder="email"
                            name="email"
                            value={user.email}
                            onChange={handleInputChange}
                            type="email"
                        />
                        <input
                            className="w-full outline-0 p-2 border border-gray-500"
                            placeholder="username"
                            name="username"
                            value={user.username}
                            onChange={handleInputChange}
                            type="text"
                        />
                        <input
                            className="w-full outline-0 p-2 border border-gray-500"
                            placeholder="password"
                            name="password"
                            value={user.password}
                            onChange={handleInputChange}
                            type="password"
                        />
                        <input
                            className="w-full outline-0 p-2 border border-gray-500"
                            placeholder="confirm your password"
                            name="rePassword"
                            value={user.rePassword}
                            onChange={handleInputChange}
                            type="password"
                        />
                        <div className="flex gap-2 items-center">
                            <label>gender</label>
                            <select
                                name="gender"
                                onChange={handleInputChange}
                                className="p-2"
                                value={user.gender}
                            >
                                <option value={"male"}>male</option>
                                <option value={"female"}>female</option>
                            </select>
                        </div>
                        <input
                            className="w-full outline-0 p-2 border border-gray-500"
                            name="photo"
                            type="file"
                            ref={pictureRef}
                        />
                    </div>
                    <div className="w-full m-auto">
                        <button className="m-auto w-full block bg-c-blue-950 py-3
                         text-white hover:bg-c-blue-700 text-xl font-semibold"
                         onClick={signUp}
                        >
                            sign up
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

export default useSignUp;