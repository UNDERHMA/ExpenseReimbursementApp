async function submitRequest() {
    try{
        let amount = document.getElementsByName('amount')[0].value;
        let requestDescription = document.getElementsByName('request-description')[0].value;
        let newRequestBody = new URLSearchParams();
        newRequestBody.append("amount", amount);
        newRequestBody.append("requestDescription", requestDescription);
        let response = await fetch(`/Expense_Reimbursements_App_war/Reports`,{ 
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
              },
              body: newRequestBody
        })
        // display invalid if no redirect occurs
        if(response.status === 201 ) {
            document.getElementById("request-submitted").style.display = "flex";
        }
        else{
            document.getElementById("request-failure").style.display = "flex";
        }
        document.getElementById("request-submit").style.display = "none";
    } catch(error) {
        console.log(error);
    }
}

async function submitRequestListener() {
    const requestSubmittedListener = document.getElementById('new-request-form');
    requestSubmittedListener.addEventListener('submit', submitRequest);
}

submitRequestListener();