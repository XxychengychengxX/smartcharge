var token = new Object();
window.addEventListener("load", gettoken(token), false);
function gettoken(str) {
    var url = location.href;
    var num = url.indexOf("?");
    token.value = url.substr(num + 1);
}
//个人信息
//window.addEventListener("load", c(),false);
function getname() {
    var xhr = new XMLHttpRequest()
    xhr.open('GET', 'user/getUsername', true)
    xhr.onload = function () {
        //返回json格式，需要解析
        var res = xhr.responseText
        var showname = document.getElementById("show_name");
        showname.innerHTML = res;
        console.log(res)
    }
    xhr.setRequestHeader('Authorization', token.value)
    xhr.send()

}
//获取车辆
function getcar() {
    //发送ajax请求 判断车辆是否注册
    var xhr = new XMLHttpRequest()
    xhr.open('GET', 'car/getCar', true)
    xhr.onload = function () {
        var res = JSON.parse(xhr.responseText)
        console.log(JSON.parse(xhr.responseText))
        console.log(res.message)

        //进行条件判断
        var showbatterysize = document.getElementById("show_batterysize");
        var showcardeck = document.getElementById("show_cardeck");

        if (res.code === 200) {
            //已有车辆
            document.getElementById("div2_table1").style.display = "block";
            var r = JSON.parse(res.resBody)
            showbatterysize.innerHTML = r.batterySize;
            showcardeck.innerHTML = r.carDeck;
        }
        else {
            //未有车辆
            document.getElementById("div2_form1").style.display = "block";
            var batterySize = document.getElementById('batterySize1')
            var ceDeck = document.getElementById('ceDeck1')
            div2_form1.onsubmit = function (e) {
                e.preventDefault()

                var xhr = new XMLHttpRequest()
                xhr.open('POST', 'car/register', true)
                xhr.onload = function () {
                    var res = JSON.parse(xhr.responseText)
                    console.log(JSON.parse(xhr.responseText))
                    console.log(res.message)
                }
                xhr.setRequestHeader('Authorization', token.value)
                xhr.setRequestHeader('content-type', 'application/x-www-form-urlencoded')
                xhr.send('batterySize=' + batterySize.value + '&carDeck=' + ceDeck.value)

                showbatterysize.innerHTML = batterySize.value;
                showcardeck.innerHTML = ceDeck.value;
                alert('您的车辆注册成功')
                document.getElementById("div2_form1").style.display = "none";
                document.getElementById("div2_table1").style.display = "block";
            }
        }
    }
    xhr.setRequestHeader('Authorization', token.value)
    xhr.send()
}

//申请充电
function applycharging() {
    //发送ajax请求
    fast = document.getElementById("fast")
    Amount = document.getElementById("Amount")
    totalAmount = document.getElementById("totalAmount")
    div3_form1.onsubmit = function (e) {
        e.preventDefault()

        var xhr = new XMLHttpRequest()
        xhr.open('POST', 'charge', true)
        xhr.onload = function () {
            var res = JSON.parse(xhr.responseText)
            console.log(JSON.parse(xhr.responseText))
            console.log(res.message)
            alert(res.message)

        }
        xhr.setRequestHeader('Authorization', token.value)
        xhr.setRequestHeader('content-type', 'application/x-www-form-urlencoded')
        xhr.send('fast=' + fast.value + '&amount=' + Amount.value + '&totalAmount=' + totalAmount.value)
    }
}
//历史订单
function getorder(p, num) {
    console.log("p:" + p)
    console.log("num:" + num)
    var xhr = new XMLHttpRequest()
    xhr.open('GET', 'charge/userHistoryBills' + '?page=' + p, true)
    xhr.onload = function () {
        var res = JSON.parse(xhr.responseText)
        var data = (JSON.parse(res.resBody))

        if (num == 1) {
            for (i = 0; i < data.length; i++) {
                var table = document.getElementById("div4_table1");

                var tr = document.createElement("tr");
                show_orderid = document.createElement("th");
                show_orderstarttime = document.createElement("th");
                show_orderupdatetime = document.createElement("th");
                show_starttime = document.createElement("th");
                show_chargeduration = document.createElement("th");
                show_chargetype = document.createElement("th");
                show_chargenumber = document.createElement("th");
                show_chargedegree = document.createElement("th");
                show_totalfee = document.createElement("th");
                show_orderisdone = document.createElement("th");

                tr.id = "div4_table1_tr" + (i + 1);
                show_orderid.innerHTML = i + 1;
                show_orderstarttime.innerHTML = data[i].created_at;
                show_orderupdatetime.innerHTML = data[i].updated_at;
                show_starttime.innerHTML = data[i].chargeStartTime;
                show_chargeduration.innerHTML = data[i].chargeDuration;
                if (data[i].chargeMod == true) {
                    show_chargetype.innerHTML = "快充"
                }
                else {
                    show_chargetype.innerHTML = "慢充"
                }
                show_chargenumber.innerHTML = data[i].deviceID;
                show_chargedegree.innerHTML = data[i].chargeAmount;
                show_totalfee.innerHTML = data[i].totalFee;
                if (data[i].isDone == true) {
                    show_orderisdone.innerHTML = "已完成";
                }
                else {
                    show_orderisdone.innerHTML = "未完成";
                }

                tr.appendChild(show_orderid);
                tr.appendChild(show_orderstarttime);
                tr.appendChild(show_orderupdatetime);
                tr.appendChild(show_starttime);
                tr.appendChild(show_chargeduration);
                tr.appendChild(show_chargetype);
                tr.appendChild(show_chargenumber);
                tr.appendChild(show_chargedegree);
                tr.appendChild(show_totalfee);
                tr.appendChild(show_orderisdone);
                table.appendChild(tr);
            }
        }
        else {
            for (i = 0; i < data.length; i++) {
                var show_orderid = document.createElement("th");
                var show_orderstarttime = document.createElement("th");
                var show_orderupdatetime = document.createElement("th");
                var show_starttime = document.createElement("th");
                var show_chargeduration = document.createElement("th");
                var show_chargetype = document.createElement("th");
                var show_chargenumber = document.createElement("th");
                var show_chargedegree = document.createElement("th");
                var show_totalfee = document.createElement("th");
                var show_orderisdone = document.createElement("th");

                var cells = document.getElementById("div4_table1_tr" + (i + 1)).cells;
                cells[0].innerHTML = i + 1 + (p - 1) * 5;
                cells[1].innerHTML = data[i].created_at;
                cells[2].innerHTML = data[i].updated_at;
                cells[3].innerHTML = data[i].chargeStartTime;
                cells[4].innerHTML = data[i].chargeDuration;
                if (data[i].chargeMod == true) {
                    cells[5].innerHTML = "快充"
                }
                else {
                    cells[5].innerHTML = "慢充"
                }
                cells[6].innerHTML = data[i].deviceID;
                cells[7].innerHTML = data[i].chargeAmount;
                cells[8].innerHTML = data[i].totalFee;
                if (data[i].isDone == true) {
                    cells[9].innerHTML = "已完成";
                }
                else {
                    cells[9].innerHTML = "未完成";
                }

            }
            if (data.length != 5) {
                for (j = 5; j > data.length; j--) {
                    if (document.getElementById("div4_table1_tr" + j)) {
                        var cell = document.getElementById("div4_table1_tr" + j).cells;
                        cell[0].innerHTML = null
                        cell[1].innerHTML = null;
                        cell[2].innerHTML = null;
                        cell[3].innerHTML = null;
                        cell[4].innerHTML = null;
                        cell[5].innerHTML = null;
                        cell[6].innerHTML = null;
                        cell[7].innerHTML = null;
                        cell[8].innerHTML = null;
                        cell[9].innerHTML = null;
                    }
                }
            }
        }
    }
    xhr.setRequestHeader('Authorization', token.value)
    xhr.send()
}
//上一页
var p = 1
var num = 1;
function pagesub() {
    if (p > 1) {
        p--
    }
    num++
    console.log(p)
    getorder(p, num)
}
//下一页
function pageadd() {
    p++
    num++
    console.log(p)
    getorder(p, num)
}
//当前订单
function currendorder() {
    var xhr = new XMLHttpRequest()
    xhr.open('GET', 'charge', true)
    xhr.onload = function () {
        var res = JSON.parse(xhr.responseText)
        if (res.code === 200) {
            document.getElementById("div5").style.display = "block";
            var show_chargetype = document.getElementById("th6")
            var show_chargenumber = document.getElementById("th7");
            var show_chargedegree = document.getElementById("th8");
            var show_chargestatus = document.getElementById("th10");

            if (res.fast) {
                show_chargetype.innerHTML = "快充"
            }
            else {
                show_chargetype.innerHTML = "慢充"
            }
            show_chargenumber.innerHTML = res.deviceID
            show_chargedegree.innerHTML = res.amount
            show_chargestatus.innerHTML = res.status
        }
        else {
            document.getElementById("div5").style.display = "none";
            alert('当前没有订单')
        }
    }
    xhr.setRequestHeader('Authorization', token.value)
    xhr.send()
}
//取消订单
function cannelorder() {
    var xhr = new XMLHttpRequest()
    xhr.open('DELETE', 'charge', true)
    xhr.onload = function () {
        var res = JSON.parse(xhr.responseText)
        console.log(res)
        alert(res.message);
    }
    xhr.setRequestHeader('Authorization', token.value)
    xhr.send()
}
function modifyorder() {
    var xhr = new XMLHttpRequest()
    xhr.open('PUT', 'charge', true)
    xhr.onload = function () {
        var res = JSON.parse(xhr.responseText)
        if (res.code === 200) {
            var fast = +prompt("请输入充电类型")
            var Amount = +prompt("请输入充电度数")
            var totalAmount = +prompt("请输入汽车电池大小")
            if (fast && Amount && totalAmount) {
                var xh = new XMLHttpRequest()
                xh.open('PUT', 'charge', true)
                xh.onload = function () {
                    var re = JSON.parse(xhr.responseText)
                    console.log(re)
                }
                xh.setRequestHeader('Authorization', token.value)
                xh.setRequestHeader('content-type', 'application/x-www-form-urlencoded')
                xh.send('fast=' + fast + '&amount=' + Amount + '&totalAmount=' + totalAmount)
                alert('订单修改成功')
            }
        }
        else {
            alert("正在充电,订单修改失败")
        }
    }
    xhr.setRequestHeader('Authorization', token.value)
    xhr.send()
}
//显示控制
window.onload = function start() {
    document.getElementById("div1").style.display = "block";
    document.getElementById("div2").style.display = "none";
    document.getElementById("div3").style.display = "none";
    document.getElementById("div4").style.display = "none";
    document.getElementById("div5").style.display = "none";
    document.getElementById("button1").style.backgroundColor = "rgba(255,255,0)";

    getname();

}
//个人信息
function show1() {
    document.getElementById("div1").style.display = "block";
    document.getElementById("div2").style.display = "none";
    document.getElementById("div3").style.display = "none";
    document.getElementById("div4").style.display = "none";
    document.getElementById("div5").style.display = "none";
    document.getElementById("button1").style.backgroundColor = "rgba(255,255,0)";
    document.getElementById("button2").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button3").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button4").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button5").style.backgroundColor = "rgba(128,128,128,0.1)";

    getname();
};
//车辆注册
function show2() {
    document.getElementById("div2").style.display = "block";
    document.getElementById("div1").style.display = "none";
    document.getElementById("div3").style.display = "none";
    document.getElementById("div4").style.display = "none";
    document.getElementById("div5").style.display = "none";
    document.getElementById("button2").style.backgroundColor = "rgba(255,255,0)";
    document.getElementById("button1").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button3").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button4").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button5").style.backgroundColor = "rgba(128,128,128,0.1)";

    getcar();
}
//申请充电
function show3() {
    document.getElementById("div3").style.display = "block";
    document.getElementById("div1").style.display = "none";
    document.getElementById("div2").style.display = "none";
    document.getElementById("div4").style.display = "none";
    document.getElementById("div5").style.display = "none";
    document.getElementById("button3").style.backgroundColor = "rgba(255,255,0)";
    document.getElementById("button1").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button2").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button4").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button5").style.backgroundColor = "rgba(128,128,128,0.1)";

    applycharging();
}
//历史订单
function show4() {
    document.getElementById("div4").style.display = "block";
    document.getElementById("div1").style.display = "none";
    document.getElementById("div2").style.display = "none";
    document.getElementById("div3").style.display = "none";
    document.getElementById("div5").style.display = "none";
    document.getElementById("button4").style.backgroundColor = "rgba(255,255,0)";
    document.getElementById("button1").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button2").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button3").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button5").style.backgroundColor = "rgba(128,128,128,0.1)";

    getorder(p, num);
    num++;
}
//查看订单
function show5() {
    document.getElementById("div1").style.display = "none";
    document.getElementById("div2").style.display = "none";
    document.getElementById("div3").style.display = "none";
    document.getElementById("div4").style.display = "none";
    document.getElementById("div5").style.display = "none";
    document.getElementById("button5").style.backgroundColor = "rgba(255,255,0)";
    document.getElementById("button1").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button2").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button3").style.backgroundColor = "rgba(128,128,128,0.1)";
    document.getElementById("button4").style.backgroundColor = "rgba(128,128,128,0.1)";

    currendorder();
}
