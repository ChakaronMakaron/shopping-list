//
const form = document.querySelector(".rename-product-form");
const submitButton = document.querySelector(".rename-product-send");

const inputFieldsArr = Array.from(form);
const inputFieldsToValidate = [];

// Fill the array of inputs that are to be validated
// Set an attribute "valid" to mark initial validation status
inputFieldsArr.forEach(e => {
    if (e.classList.contains("rename-product-input")) {
        e.setAttribute("valid", "1"); // <- Default is valid for this case
        inputFieldsToValidate.push(e);
    }
});

console.log("Inputs:");
inputFieldsToValidate.forEach(e => {console.log(e.value)});

console.log("To validate:");
console.log(inputFieldsToValidate);

// Validation while typing
form.addEventListener("input", inputHandler);

function inputHandler({ target }) {
    const inputValue = target.value;

    const regexp = new RegExp("\\w+");

    if (regexp.test(inputValue)) {
        target.style.border = "2px solid rgb(0, 196, 0)";
        target.setAttribute("valid", "1");
    } else {
        target.style.border = "2px solid rgb(255, 0, 0)";
        target.setAttribute("valid", "0");
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


