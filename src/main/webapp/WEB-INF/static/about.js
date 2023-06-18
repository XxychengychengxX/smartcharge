window.onload = function start(){
    document.getElementById("div1").style.display = "block";
    document.getElementById("div2").style.display = "none";
    document.getElementById("div3").style.display = "none";
    document.getElementById("button1").style.backgroundColor = "violet";
}
function show1() {
    document.getElementById("div1").style.display = "block";
    document.getElementById("div2").style.display = "none";
    document.getElementById("div3").style.display = "none";
    document.getElementById("button1").style.backgroundColor = "violet";
    document.getElementById("button2").style.backgroundColor = "rgba(0, 255, 255, 1)";
    document.getElementById("button3").style.backgroundColor = "rgba(0, 255, 255, 1)";
};
function show2() {
    document.getElementById("div2").style.display = "block";
    document.getElementById("div1").style.display = "none";
    document.getElementById("div3").style.display = "none";
    document.getElementById("button2").style.backgroundColor = "violet";
    document.getElementById("button1").style.backgroundColor = "rgba(0, 255, 255, 1)";
    document.getElementById("button3").style.backgroundColor = "rgba(0, 255, 255, 1)";
}
function show3() {
    document.getElementById("div3").style.display = "block";
    document.getElementById("div1").style.display = "none";
    document.getElementById("div2").style.display = "none";
    document.getElementById("button3").style.backgroundColor = "violet";
    document.getElementById("button1").style.backgroundColor = "rgba(0, 255, 255, 1)";
    document.getElementById("button2").style.backgroundColor = "rgba(0, 255, 255, 1)";
}