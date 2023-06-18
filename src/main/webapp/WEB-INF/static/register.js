// 要操作到的元素
let login = document.getElementById('login');
let register = document.getElementById('register');
let form_box = document.getElementsByClassName('form-box')[0];
let register_box = document.getElementsByClassName('register-box')[0];
let login_box = document.getElementsByClassName('login-box')[0];
// 去注册按钮点击事件
register.addEventListener('click', () => {
    form_box.style.transform = 'translateX(80%)';
    login_box.classList.add('hidden');
    register_box.classList.remove('hidden');
})
// 去登录按钮点击事件
login.addEventListener('click', () => {
    form_box.style.transform = 'translateX(0%)';
    register_box.classList.add('hidden');
    login_box.classList.remove('hidden');
})
//注册检测
//获取元素
var regForm = document.getElementById('register_form')
var regName = document.getElementById('register_username')
var regPwd = document.getElementById('register_password')
var regPwd2 = document.getElementById('register_password_2')

//给form绑定一个表单提交事件
regForm.onsubmit = function (e) {
    //阻止表单的默认提交行为
    e.preventDefault()

    var name = regName.value
    var pwd = regPwd.value
    var pwd2 = regPwd2.value
    /*
    //验证用户名和密码
    var p = /^(?=\S*[a-z])(?=\S*[A-Z])(?=\S*\d)\S{6,15}$/;
    if (!p.test(name)) {
        errorName.style.display = 'inline-block';
        errorName.focus(); //获取焦点				
        return false;
    }
    return true;
    */
    //验证用户名和密码
    var p = /^[A-Za-z0-9]{6,15}$/;
    if (!p.test(name))
        return alert("用户名不合法");
    if (pwd != pwd2) 
        return alert('俩次密码输入不同');

    //发送ajax请求
    var xhr = new XMLHttpRequest()
    xhr.open('POST', 'user/register', true)
    xhr.onload = function () {
        //返回json格式，需要解析
        console.log(JSON.parse(xhr.responseText))
        var res = JSON.parse(xhr.responseText)
        console.log(res.message)
        //进行条件判断
        if (res.message === "注册成功") {
            //注册成功
            window.location.href = './register.html'
            alert('注册成功')
        } else {
            //注册失败
            alert('用户名已存在，注册失败')
        }
    }

    xhr.setRequestHeader('content-type', 'application/x-www-form-urlencoded')
    xhr.send('username=' + name + '&password=' + pwd)

    console.log(name, pwd)
}
//登录检测
//获取元素
var loginForm = document.getElementById('login_form')
var loginName = document.getElementById('login_username')
var loginPwd = document.getElementById('login_password')

//给form绑定一个表单提交事件
loginForm.onsubmit = function (e) {
    //注意：要阻止表单的默认提交行为
    e.preventDefault()

    //拿到用户填写的用户名和密码
    var name = loginName.value
    var pwd = loginPwd.value

    //验证用户名和密码
    if (!name) return alert('请填写账号！')
    if (!pwd) return alert('请填写密码！')

    //发送ajax请求
    var xhr = new XMLHttpRequest()
    xhr.open('POST', 'user/login', true)
    xhr.onload = function () {
        //返回json格式，需要解析
        var res = JSON.parse(xhr.responseText)
        console.log(JSON.parse(xhr.responseText))
        console.log(res.message)
        //进行条件判断
        if (res.message === "欢迎光临,登录成功!") {
            //登录成功
            window.location.href = "./home.html?" + res.token + "";
        }
        else {
            //登录失败
            alert('用户名或密码错误')
        }
    }

    xhr.setRequestHeader('content-type', 'application/x-www-form-urlencoded')
    xhr.send('username=' + name + '&password=' + pwd)

    console.log(name, pwd)
}