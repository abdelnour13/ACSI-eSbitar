const password = document.getElementById(document.body.dataset.controlledId);
const showHide = document.getElementById(document.body.dataset.buttonId);
const img = showHide.children.item(0);

let isHidden = true;

showHide.addEventListener('click', (event) => {
    event.preventDefault();
    
    if(isHidden) 
    {
        img.src = `icons/show.png`;
        password.type = 'text';
    } else {
        img.src = `icons/hide.png`;
        password.type = 'password';
    }
    isHidden = !isHidden;
});