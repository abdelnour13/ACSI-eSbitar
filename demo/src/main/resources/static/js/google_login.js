const googleButoon = document.getElementById("buttonDiv");
googleButoon.style.backgroundColor = 'white';

function parseJwt (token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var jsonPayload = decodeURIComponent(window.atob(base64).split('')
  .map(function(c) {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));

  return JSON.parse(jsonPayload);
}

function handleCredentialResponse(response) {
  const user = parseJwt(response.credential);
  user.gender = user.gender ? user.gender : 'unkown';
  user.active = user['email_verified'];

  const form = new FormData();
  form.append('email', user.email);
  form.append('name', user.name);
  form.append('picture', user.picture);
  form.append('gender', user.gender);
  form.append('active', user.active);

  axios.post('http://localhost:8080/google/login',form, {
    Headers: {
      'Content-Type':'multipart/form-data'
    }
  }).then(res => {
    const token = res.data;
    document.cookie = "auth="+token;
    window.location = 'http://localhost:8080/home';
  })
  .catch(err => console.log(err))
}

window.onload = function () {
  google.accounts.id.initialize({
    client_id: "756158280165-us54lufm29b8n1guidoufu8elmaod30q.apps.googleusercontent.com",
    callback: handleCredentialResponse
  });
  google.accounts.id.renderButton(
    googleButoon,
    { theme: "outline", size: 'large' }  
  );
  google.accounts.id.prompt();
}