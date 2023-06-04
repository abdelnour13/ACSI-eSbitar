import axios from "axios";


const api = {};

const url = 'http://localhost:8080'

api.getUserById = async (id) => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.get(`${url}/users/${id}`, {
            headers: {
                Authorization: token
            }
        })
        console.log(response)
        return [response.data.user,null];
    } catch(err) {
        return [null,err.reposne.data.error];
    }
}

export default api;