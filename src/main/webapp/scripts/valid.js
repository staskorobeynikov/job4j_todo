function validate(data) {
    let answer = '';
    for (let i = 0; i < data.length; i++) {
        if (data[i].val() === '') {
            answer += data[i].attr("placeholder") + "\n";
        }
    }
    if (answer !== '') {
        event.preventDefault()
        alert(answer);
        return false;
    }
    return true;
}