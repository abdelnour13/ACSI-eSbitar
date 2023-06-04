import { useEffect, useState } from "react";
import axios from "axios";
import { useGoogleLogin,googleLogout } from '@react-oauth/google';
import google from '../icons/google.png';
import { useNavigate } from "react-router-dom";
const url = 'http://localhost:8080';

function GoogleSignUp() {

    const [user, setUser] = useState(false); 
    const navigate = useNavigate();

    const login = useGoogleLogin({
        onSuccess: (user) => setUser(user),
        onError: (err) => console.log(err)
    });

    const logOut = () => {
        googleLogout();
    };

    useEffect(() => {
        if (user) {
            axios
                .get(`https://www.googleapis.com/oauth2/v1/userinfo?access_token=${user.access_token}`, {
                    headers: {
                        Authorization: `Bearer ${user.access_token}`,
                        Accept: 'application/json'
                    }
                })
                .then(res => {
                    const user = {
                        ...res.data,gender:'unkown',active: true
                    }
                    return axios.post(`${url}/auth/google/login`, user)
                })
                .then(res => {
                    localStorage.setItem('token', res.data.token);
                    navigate('/profile');
                    //console.log(res.data);
                    logOut();
                })
                .catch((err) => console.log(err));
        }
    },[user, navigate]);

    return(
        <div>
            <button 
                type="submit" 
                className="w-full flex justify-center items-center
                bg-gray-200 py-2 text-c-blue-950 text-xl font-semibold gap-2" 
                onClick={(ev) => {
                    ev.preventDefault();
                    login();
                }}
            >
                <img src={google} alt='' className="h-6 aspect-square"/>
                <p>google</p>
            </button>
        </div>
    );
}

export default GoogleSignUp;