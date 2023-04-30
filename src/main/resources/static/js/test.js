
const arr = [1, 1, 0, 1];
const result = arr.reduce((accum, el) => {
    return accum && el;
});

console.log("result: " + result);
console.log(result === 0);
