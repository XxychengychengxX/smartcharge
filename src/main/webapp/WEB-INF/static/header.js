var token = new Object();
window.addEventListener("load", getname(token), false);
function getname(str) {
    var url = location.href;
    var num = url.indexOf("?");
    token.value = url.substr(num + 1);
};
console.log(token.value);
document.writeln("<nav class=\'top\'>");
document.writeln("    <div>");
document.writeln("        <div>");
document.writeln("            <ul style=\'margin-top: 25px\'>");
document.writeln("                <li><img src=\'./picture/bupt.jpg\'></li>");
document.writeln("                <li><a href=\'./home.html?"+ token.value +"\'>首页</a></li>");
document.writeln("                <li><a href=\'./about.html?"+ token.value +"\'>关于我们</a></li>");
document.writeln("                <li><a href=\'./serve.html?"+ token.value +"\'>服务与支持</a></li>");
document.writeln("            </ul>");
document.writeln("        </div>");
document.writeln("    </div>");
document.writeln("</nav>");

//xhr.setRequestHeader("Authorization","token.value")