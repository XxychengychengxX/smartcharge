var i = 1;
var m = 1140;
//window.onload代表页面加载即执行
window.onload = function start() {
    ti();
}
function ti() {
    tim = setInterval("pho()", 3000);//定义一个3000毫秒执行一次的计时器,执行pho()函数
    color(i);//引用color()函数,自己写的,在下面
}
function pho() {
    if (i == 3) {
        i = 0;
    }
    i = i + 1;
    magicti();//引入幻灯片效果
    color(i);
    if (i == 3) {
        i = 0;
    }
    //if分支表示1000毫秒执行一次,当i等于3就是第三个图片时候i=0,下一张执行就是i+1,即第一张
}
function magicti() {
    //简单的幻灯片效果
    //mti = setInterval("magic()", 1);//每1毫秒图片向左滑动
    d1.style.background = "url(picture/" + i + ".png)";
    d1.style.backgroundRepeat = "no-repeat";
    d1.style.backgroundSize = "cover";
}

/* function magic() {
    //简单的幻灯片效果
    m = m - 3;
    var d1 = document.getElementById("d1");
    d1.style.backgroundPosition = m + "px" + "0px";
    d1.style.backgroundRepeat = "no-repeat";
    if (m == 0) {
        m = 1260;
        clearInterval(mti);
    }
} */
function color(i) {
    //color()的作用在于根据展示不同的图片改变顺序点的颜色
    var d21 = document.getElementById("d21");
    var d22 = document.getElementById("d22");
    var d23 = document.getElementById("d23");
    //以上获取那三个点
    //下面是一个switch分支结构
    switch (i) {
        case 1:
            d21.style.background = "black";
            d22.style.background = "rgb(0, 140, 255)";
            d23.style.background = "rgb(0, 140, 255)";
            break;
        case 2:
            d21.style.background = "rgb(0, 140, 255)";
            d22.style.background = "black";
            d23.style.background = "rgb(0, 140, 255)";
            break;
        case 3:
            d21.style.background = "rgb(0, 140, 255)";
            d22.style.background = "rgb(0, 140, 255)";
            d23.style.background = "black";
            break;
    }
}
function off() {
    //鼠标悬浮停止播放
    //停止俩计时器
    clearInterval(tim);
    clearInterval(mti);
}
function on() {
    //鼠标离开重启计时器,开始播放
    ti();
}
function add() {
    i = i + 1;
    magicti();//引入幻灯片效果
    clearInterval(mti);
    color(i);//停止计时器,防止计时器叠加
    if (i == 3) {
        i = 0;
    }
}
function noadd() {
    i = i - 1;
    if (i == 0) {
        i = 3;
    }
    magicti();//引入幻灯片效果
    clearInterval(mti);//停止计时器,防止计时器叠加
    color(i);
}