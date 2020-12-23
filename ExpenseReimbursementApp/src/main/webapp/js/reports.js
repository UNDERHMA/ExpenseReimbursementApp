async function getRequests() {
    try{
        let origin = location.pathname;
        let response = await fetch(`/Expense_Reimbursements_App_war/Reports?origin=${origin}`,{ 
            credentials: "same-origin"});
        return await response.json();
    } catch(error) {
        console.log(error);
    }
}

async function renderRequests() {

    let requests = await getRequests();
    let html;
    if(Object.getOwnPropertyNames(requests).length !== 0) {
        let pathName = location.pathname;
        let reviewed = true;
        if(pathName.localeCompare(
            '/Expense_Reimbursements_App_war/pendingreimbursementrequests.html') === 0) {
            reviewed = false;
        }
        html = `<table class="table table-bordered" id="request-table">
                <thead>
                    <tr>
                        <th>
                            Request Id :
                        </th>
                        <th>
                            Status :
                        </th>
                        <th>
                            Amount Requested :
                        </th>
                        <th>
                            Submitted By :
                        </th>
                        <th>
                            Reviewed By :
                        </th>
                    </tr>
                </thead>
                <tbody>`;
        requests.forEach(request => {
            let reviewedFirstName = '';
            let reviewedLastName = '';
            if(reviewed) {
                reviewedFirstName = request.accountReviewed.firstName;
                reviewedLastName = request.accountReviewed.lastName;
            }
            let htmlRow = `<tr class="request">
                                <td id="requests-id">${request.reimbursementRequestId}</td>
                                <td id="requests-status">${request.reimbursementRequestStatus.reimbursementRequestStatusDesc}</td>
                                <td id="requests-amount">${request.amount}</td>
                                <td id="requests-submitted-by">${request.accountSubmitted.firstName} ${request.accountSubmitted.lastName}</td>
                                <td id="requests-reviewed-by"> ${reviewedFirstName} ${reviewedLastName}</td>
                            </tr>
                            <tr id="hidden-description">
                                    <td colspan="1">
                                        Request Description :
                                    </td>
                                    <td colspan="3">${request.requestDescription}</td>
                                    <td colspan="1">
                                            <form id="approve-or-deny" onsubmit="return false;">
                                                <button type="submit" class="btn btn-primary" id="request-approved">Approve Request</button>
                                                <button type="submit" class="btn btn-primary" id="request-denied">Deny Request</button>
                                            </form>
                                            <p id=successful-approve-or-deny> Update Completed Successfully.</>
                                            <p id=error-approve-or-deny> Error Occurred. Please contact Support.</>
                                    </td>
                            </tr>`
                        html += htmlRow;
        });
        html += `</tbody>
                </table>`
    }
    else {
        // add a message on the DOM that there are no requests to display
        html = `<h1>There are no reimbursement requests to display.</h1>`
    }

    let divToAddTo = document.querySelector('.tablebody');
    divToAddTo.innerHTML = html;
}

function expandReport() {
    let description = this.nextElementSibling;
    if (description.style.display !== "table-row") {
        description.style.display = "table-row";
        const userCookie = document.cookie.split('; ').find(cookie => 
            cookie.startsWith('expenseapp_authenticationcookie')); 
        if(userCookie.includes("Manager") && window.location.pathname == "/Expense_Reimbursements_App_war/pendingreimbursementrequests.html")  {
            let buttons = description.lastElementChild.firstElementChild;
            buttons.style = "display: flex;"
            let requestId = this.firstElementChild.innerHTML;
            let approve = 2;
            let deny = 3;
            buttons.firstElementChild.addEventListener('click', function() {approveOrDeny.bind(this)(approve,requestId);});
            buttons.lastElementChild.addEventListener('click', function() {approveOrDeny.bind(this)(deny,requestId);});
        }
      } else {
        description.style.display = "none";
      }
}

async function approveOrDeny(action,requestId) {
    try{
        let approveOrDenyBody = new URLSearchParams();
        approveOrDenyBody.append("requestId", requestId);
        approveOrDenyBody.append("status", action);
        let response = await fetch(`/Expense_Reimbursements_App_war/Reports`,{ 
            method: 'PUT',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            },
            body: approveOrDenyBody
        })
        // display error message if not successful on backend
        if(response.status === 500 ) {
            this.parentElement.style.display = "none";
            this.parentElement.nextElementSibling.nextElementSibling.style.display = "block";

        }
        else{
            this.parentElement.style.display = "none";
            this.parentElement.nextElementSibling.style.display = "block";
        }
    } catch(error) {
        console.log(error);
    }
}

async function addDescriptions() {
    await renderRequests();
    const rows = document.getElementsByClassName('request');
    for(let i = 0; i < rows.length; i++) {
        console.log(rows.item(i).innerHTML);
        rows.item(i).addEventListener('click', expandReport);
    }
}

addDescriptions();