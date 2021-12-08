function changeBorders() {
    var names=[document.getElementById('borderImageCol'),document.getElementById('borderNameCol'),document.getElementById('borderLink')];
    if (names[0].className=="col") names.forEach(name => name.className="col border border-primary rounded");
    else names.forEach(name => name.className="col");

};


//First time using js, don't know how many time it took me just to do this, I learnt a lot though
function darkMode(){
    var bd=document.getElementById('bd');
    var triggerButton=document.getElementById('btn');
    var param=window.getComputedStyle(document.body, null).getPropertyValue('background-color');

    if(param=="rgb(255, 255, 255)"){
        bd.style.backgroundColor="#181A1B";
        bd.style.color="white";
        triggerButton.className="btn btn-dark"
        sessionStorage.setItem("theme", "dark");

    }
    else{
        bd.style.backgroundColor="white";
        bd.style.color="black";
        triggerButton.className="btn btn-light"
        sessionStorage.setItem("theme", "light");
    }

};

function restore(){
    var bd=document.getElementById('bd');
    var theme=sessionStorage.getItem("theme");
    var triggerButton=document.getElementById('btn');

    if(theme=="dark"){
        bd.style.backgroundColor="#181A1B";
        bd.style.color="white";
        triggerButton.className="btn btn-dark"

    }else{
        bd.style.backgroundColor="white";
        bd.style.color="black";
        triggerButton.className="btn btn-light"
    }
}

function bcg(event){
    var bd=document.body;
    document.body.style.background="linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, 1)), url('"+event.target.id+"')";
    bd.style.backgroundSize="cover";

}