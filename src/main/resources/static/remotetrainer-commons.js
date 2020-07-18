function handleErrorResponse(response) {
    response.json().then(data => {
        if(data.errorMessage) {
            onError(data.errorMessage);
        }
    });
}

function onError(message) {
    $('#toast-type').text('Error');
    toast(message);
}

function onSuccess(message) {
    $('#toast-type').text('Exito');
    toast(message);
}

function toast(errorMessage) {
    $('.modal').modal('hide');
    $('#error-msg').text(errorMessage);
    $('#error-toast').toast('show');
}

$(function () {
    $('[data-toggle="tooltip"]').tooltip();
});

/**
 * Removes the element for the given id
 * @param elementId
 */
function remove(elementId) {
    $(`#${elementId}`).remove();
}

/**
 * Validates that the text inside the selector has content.
 * If the content is valid, then hides the error messages. If not, shows them.
 * @param tagId, which has a ${selector}-feedback div that shows the error
 * @returns {boolean} if the selector is valid
 */
function validateIsPresent(tagId) {
    if($(`#${tagId}`).val()) {
        $(`#${tagId}`).css('border-color', '');
        $(`#${tagId}-feedback`).hide();
        return true;
    } else {
        $(`#${tagId}`).css('border-color', 'red');
        $(`#${tagId}-feedback`).show();
        return false;
    }
}

/**
 * Validates that the text inside the selector has content and its a number.
 * If the content is valid, then hides the error messages. If not, shows them.
 * @param tagId, which has a ${selector}-feedback div that shows the error
 * @returns {boolean} if the selector is valid
 */
function validateNumber(tagId) {
    const contentValue = $(`#${tagId}`).val();

    if(!contentValue || isNaN(contentValue)) {
        $(`#${tagId}`).css('border-color', 'red');
        $(`#${tagId}-feedback`).show();
        return false;
    } else {
        $(`#${tagId}`).css('border-color', '');
        $(`#${tagId}-feedback`).hide();
        return true;
    }
}