//
const form = document.querySelector(".reg-form");
const submitButton = document.querySelector(".reg-btn");

const inputFieldsArr = Array.from(form);
const inputFieldsToValidate = [];

// Fill the array of inputs that are to be validated
// Set an attribute "valid" to mark initial validation status
inputFieldsArr.forEach(e => {
    if (e.hasAttribute("regex")) {
        e.setAttribute("valid", "0");
        inputFieldsToValidate.push(e);
    }
});

// Validation while typing
form.addEventListener("input", inputHandler);

function inputHandler({ target }) {
    if (target.hasAttribute("regex")) {
        inputCheck(target);
    }
}

function inputCheck(element) {
    const inputValue = element.value;
    const elementRegExString = element.getAttribute("regex");
    const elementRegEx = new RegExp(elementRegExString);

    if (elementRegEx.test(inputValue)) {
        element.style.border = "2px solid rgb(0, 196, 0)";
        element.setAttribute("valid", "1");
    } else {
        element.style.border = "2px solid rgb(255, 0, 0)";
        element.setAttribute("valid", "0");
    }
}

// Validation on "submit" click
submitButton.addEventListener("click", buttonHandler);

function buttonHandler(event) {
    const validationStatusArr = [];

    function highlightInvalid() {
        for (let i = 0; i < validationStatusArr.length; i++) {
            if (validationStatusArr[i] === 0) {
                inputFieldsToValidate[i].style.border = "2px solid rgb(255, 0, 0)";
            }
        }
    }

    // Getting validation status for every field
    inputFieldsToValidate.forEach(e => {
        validationStatusArr.push(parseInt(e.getAttribute("valid")));
    });

    // Once we got the validation status for every field
    // we can highlight fields that are not OK
    highlightInvalid();

    // Whether total result is OK or not
    let result = validationStatusArr.reduce((acc, e) => {
        return acc && e;
    });

    result = parseInt(result);
    console.log("Validation satus: " + result);
    console.log(validationStatusArr);

    if (result === 0) {
        event.preventDefault();
    }
}


