function onError(response) {
    response.json().then(data => {
        $('.modal').modal('hide')
        $('#error-msg').text(data.message)
        $('#error-toast').toast('show')
    })
}