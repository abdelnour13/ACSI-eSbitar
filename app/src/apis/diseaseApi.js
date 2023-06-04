import axios from "axios";

const api = {};

const url = 'http://localhost:8080'

api.getDiseases = async (query) => {
    try {
        let search = '';
        if(query && query.search) {
            search = query.search;
        }
        const token = localStorage.getItem('token')
        const response = await axios.get(`${url}/diseases/?search=${search}`, {
            headers: {
                Authorization: token
            }
        });
        return [response.data, null];
    } catch(err) {
        return [null,err];
    }
}

api.getDiseaseById = async (id) => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.get(`${url}/diseases/${id}/`, {
            headers: {
                Authorization: token
            }
        });
        return [response.data,null];
    } catch(err) {
        return [null,err];
    }
}

api.createDisease = async ({ name,symptoms }) => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.post(`${url}/diseases/`, 
            {
                name,
                symptoms: symptoms
            },
            {
                headers: {
                    Authorization: token
                }
            }
        );
        const id = response.data;
        return await api.getDiseaseById(id);
    } catch (err) {
        return [null,err];
    }
}

api.deleteDisease = async (id) => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.delete(`${url}/diseases/${id}/`, {
            headers: {
                Authorization: token
            }
        });
        return [response.data, null];
    } catch(err) {
        return [null,err];
    }
}

api.updateDisease = async(id, { name }) => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.patch(`${url}/diseases/${id}/`, { name }, {
            headers: {
                Authorization: token
            }
        });
        return [response.data,null];
    } catch(err) {
        return [null,err];
    }
}

api.addSymptomToDisease = async (id, symptomId) => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.post(`${url}/diseases/${id}/symptoms/`, {
            symptom_id: symptomId
        }, {
            headers:{
                Authorization: token
            }
        });
        return [response.data, null];
    } catch(err) {
        return [null,err];
    }
}

api.removeSymptomFromDisease = async (id, symptomId) => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.delete(`${url}/diseases/${id}/symptoms/${symptomId}/`, {
            headers:{
                Authorization: token
            }
        });
        return [response.data, null];
    } catch(err) {
        return [null,err];
    }
}

export default api;