import axios from "axios";

const api = {};


const url = "http://localhost:8080";

api.getAllSymptoms = async () => {
    try {
        const response = await axios.get(url+'/symptoms/', {
            headers: {
                Authorization: localStorage.getItem('token')
            }
        });
        return [response.data,null]
    } catch(err) {
        return [null,err];
    }
}

api.createSymptom = async ({ name }) => {
    try {
        const response = await axios.post(url+'/symptoms/', { name }, {
            headers: {
                Authorization: localStorage.getItem('token')
            }
        });
        return [response.data,null];
    } catch(err) {
        return [null,err];
    }
}

api.deleteSymptom = async (id) => {
    try {
        const response = await axios.delete(url+'/symptoms/'+id+'/', {
            headers: {
                Authorization: localStorage.getItem('token')
            }
        });
        return [response.data,null];
    } catch(err) {
        return [null,err];
    }
}

api.updateSymptom = async (id, { name }) => {
    try {
        const response = await axios.put(url+'/symptoms/'+id+'/', { name }, {
            headers: {
                Authorization: localStorage.getItem('token')
            }
        });
        return [response.data, null];
    } catch(err) {
        return [null,err];
    }
} 

export default api;