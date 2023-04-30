//
const form = document.querySelector(".new-po-form");
const submitButton = document.querySelector(".new-po-send");

const inputFieldsArr = Array.from(form);
const inputFieldsToValidate = [];

// Fill the array of inputs that are to be validated
// Set an attribute "valid" to mark initial validation status
inputFieldsArr.forEach(e => {
    if (e.classList.contains("new-po-input")) {
        e.setAttribute("valid", "0");
        inputFieldsToValidate.push(e);
    }
});

console.log("To validate:");
console.log(inputFieldsToValidate);

// Validation while typing
form.addEventListener("input", inputHandler);

function inputHandler({ target }) {
    if (target.hasAttribute("regexp")) {
        inputCheck(target);
    }
}

function inputCheck(element) {
    const inputValue = element.value;
    const elementRegEx = new RegExp(element.getAttribute("regexp"));

    console.log("input: " + inputValue);
    console.log("regex: " + elementRegEx);
    console.log("testing against regex: " + elementRegEx.test(inputValue));

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


