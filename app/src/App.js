import "./App.css";

import {
  RouterProvider,
  createBrowserRouter,
} from "react-router-dom";

import jwtDecode from "jwt-decode";
import Home from "./screens/home";
import { createContext, useState } from "react";
import usersApi from './apis/usersApi';
import diseasesApi from "./apis/diseaseApi";
import Screen from "./components/screen";
import Diseases from "./screens/diseases";
import Admin from "./screens/admin";
import Context from "./ClobalContext";
import AdminDiseases from "./components/adminDiseases";

export const AppContext = createContext(null);

async function isAuthenticated() {

  const token = localStorage.getItem("token");

  if (token) {
    const decodedToken = jwtDecode(token);
    console.log(decodedToken);
    if (new Date().getTime() < decodedToken.exp * 1000) {
      const [user,] = await usersApi.getUserById(decodedToken.sub);
      console.log({isAuthenticated:true,user})
      return {
        isAuthenticated:true,
        user
      }
    }
  }

  return {isAuthenticated:false,user:null};
}

async function fetchDiseases() {
  const [diseases,] = await diseasesApi.getDiseases();
  if(diseases) {
    console.log(diseases)
    return {diseases};
  }
  return {diseases:[]};
}

function App() {
  const router = createBrowserRouter([
    {
      path:'/',
      loader: isAuthenticated,
      element: <Context />,
      children: [
        {
          path:"/",
          loader: isAuthenticated,
          element: <Screen />,
          children:[
            {
              path: '/',
              element:<Home />
            },
            {
              path:'/diseases',
              loader: fetchDiseases,
              element:<Diseases />
            },
          ],
        },
        {
          path:'/admin',
          element:<Admin />,
          children: [
            {
              path:'/admin/diseases-symptoms',
              loader:fetchDiseases,
              element:<AdminDiseases />
            }
          ]
        }
      ]
    },
  ]);

  const [lang,setLang] = useState('en');
  const [user,setUser] = useState(null);

  return (
    <AppContext.Provider value={{
      lang,
      setLang,
      user,
      setUser
    }} >
      <RouterProvider router={router} />
    </AppContext.Provider>
  )
}

export default App;