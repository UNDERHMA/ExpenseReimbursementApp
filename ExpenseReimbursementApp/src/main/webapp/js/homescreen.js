const userCookie = document.cookie.split('; ').find(cookie => 
    cookie.startsWith('expenseapp_authenticationcookie')); 
if(userCookie.includes("Manager")) {
    document.getElementById("new-request").style.display = "none";
    document.getElementById("employee-home").style.display = "none";
}
else{
    document.getElementById("manager-home").style.display = "none";
}