async function attemptLogin() {
    try{
        let username = document.getElementsByName('username')[0].value;
        let password = document.getElementsByName('password')[0].value;
        let loginBody = new URLSearchParams();
        loginBody.append("username", username);
        loginBody.append("password", password);
        let response = await fetch(`/Expense_Reimbursements_App_war/login`,{ 
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
              },
              body: loginBody
        })
        // display invalid if no redirect occurs
        if(response.status === 401 ) {
            document.getElementById("login-invalid").style.display = "block";
        }
        else{
            window.location.href = response.url;
        }
    } catch(error) {
        console.log(error);
    }
}

async function loginListener() {
    const loginSubmittedListener = document.getElementById('login-form');
    loginSubmittedListener.addEventListener('submit', attemptLogin);
}

function loginUsersWithValidCookie(){
    const loginCookie = document.cookie.split('; ').find(cookie => cookie.startsWith('expenseapp_authenticationcookie'));   
    if(loginCookie !== undefined){
        if(loginCookie.includes("Employee")) {
            window.location.href = "/Expense_Reimbursements_App_war/employeehomescreen.html";
        }
        else if(loginCookie.includes("Manager")) {
            window.location.href = "/Expense_Reimbursements_App_war/managerhomescreen.html";
        }
    }
}

loginListener();
loginUsersWithValidCookie()