function addExerciseBlock() {
    const routineBlocks = document.getElementById('routine-blocks');

    const blockContainer = document.createElement('div');
    blockContainer.className = 'container-fluid block-container';

    const collapsibleContent = document.createElement('div');
    collapsibleContent.className = 'pb-5 collapse show';
    collapsibleContent.appendChild(blockHeader());
    let exerciseBlockContainer = blockContent();
    collapsibleContent.appendChild(exerciseBlockContainer);
    collapsibleContent.appendChild(blockButtons(blockContainer, exerciseBlockContainer));

    const triggerButton = document.createElement('button');
    triggerButton.className = 'btn btn-dark block-name btn-block unnamed';
    triggerButton.innerText = `Bloque #${routineBlocks.childElementCount}`;
    triggerButton.onclick = function() {
        $(collapsibleContent).collapse('toggle');
    };

    blockContainer.appendChild(triggerButton);
    blockContainer.appendChild(collapsibleContent);

    routineBlocks.appendChild(blockContainer);
}

function blockHeader() {
    const container = document.createElement('div');

    const label = document.createElement('label');
    label.innerText = 'Notas:';
    container.appendChild(label);

    const text = document.createElement('textarea');
    text.className = 'form-control block-notes';
    container.appendChild(text);

    return container;
}

function blockButtons(blockContainer, exerciseBlockContainer) {
    const buttonContainer = document.createElement('div');
    buttonContainer.className = 'float-right';

    const addExerciseButton = document.createElement('button');
    addExerciseButton.className = 'btn btn-dark btn-sm';
    addExerciseButton.innerText = 'Agregar ejercicio';
    addExerciseButton.onclick = function() {
        $(exerciseBlockContainer).find('tbody').append(exerciseRow());
    };

    const deleteBlockButton = document.createElement('button');
    deleteBlockButton.className = 'btn btn-secondary btn-sm';
    deleteBlockButton.innerText = 'Eliminar bloque';
    deleteBlockButton.onclick = function() {
        $(blockContainer).remove();
        renameBlocks();
    };

    buttonContainer.appendChild(deleteBlockButton);
    buttonContainer.appendChild(addExerciseButton);

    return buttonContainer;
}

function blockContent() {
    const table = document.createElement('table');
    table.className = 'table table-stripped';

    const header = document.createElement('thead');
    table.appendChild(header);
    const headerRow = document.createElement('tr');
    header.appendChild(headerRow);
    headerRow.appendChild(tableColumn('Ejercicio'));
    headerRow.appendChild(tableColumn('Repeticiones'));
    headerRow.appendChild(tableColumn(''));

    const body = document.createElement('tbody');
    body.className = 'block-exercises';
    table.appendChild(body);

    return table;
}

function tableColumn(innerText) {
    const th = document.createElement('th');
    th.scope = 'col';
    th.innerText = innerText;

    return th;
}

function exerciseRow() {
    const tr = document.createElement('tr');

    const select = document.createElement('select');
    select.className = 'block-exercise';
    exercises.forEach(function(exercise){
        const option = document.createElement('option');
        $(option).data(exercise);
        option.innerText = exercise.name;
        select.appendChild(option);
    });

    const exerciseTh = document.createElement('th');
    exerciseTh.scope = 'col';
    exerciseTh.appendChild(select);
    tr.appendChild(exerciseTh);

    const quantityTh = document.createElement('th');
    quantityTh.scope = 'col';
    const quantityInput = document.createElement('input');
    quantityInput.className = 'block-quantity';
    quantityTh.appendChild(quantityInput);
    tr.appendChild(quantityTh);

    const removeTh = document.createElement('th');
    removeTh.scope = 'col';
    const removeBtn = document.createElement('button');
    removeBtn.type = 'button';
    removeBtn.className = 'btn btn-dark btn-sm';
    removeBtn.innerText = 'X';
    removeTh.appendChild(removeBtn);
    tr.appendChild(removeTh);

    removeBtn.onclick = function(){
        $(tr).remove();
    };

    return tr;
}

function renameBlocks() {
    $('#routine-blocks').children('.block-container').children('.block-name').each(function(index, nameContainer) {
        if ($(nameContainer).hasClass('unnamed')) {
            nameContainer.innerText = `Bloque #${index}`;
        }
    });
}

function createRoutine(routineId) {
    const body = extractRoutine(routineId);

    fetch('/api/routines', {
        method: 'POST',
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(function(response) {
        if(response.ok) {
            // TODO add date
            window.location.href = '/routines';
        } else {
            onError(response)
        }
    })
}

function extractRoutine(routineId) {
    return {
        'id': routineId,
        'date': $('#routine-create-date').val(),
        'shortDescription': $('#routine-create-description').val(),
        'exerciseBlocks': extractBlocks(),
        'notes': $('#routine-create-notes').val()
    }
}

function extractBlocks() {
    const blocks = [];

    $('#routine-blocks .block-container').each(function() {
        blocks.push(extractBlock(this))
    });

    return blocks;
}

function extractBlock(blockContainer) {
    if (jQuery.isEmptyObject($(blockContainer).data())) {
        return {
            'notes': $(blockContainer).find('.block-notes').val(),
            'exercises': extractBlockExercises(blockContainer)
        }
    } else {
        return $(blockContainer).data();
    }
}

function extractBlockExercises(blockContainer) {
    const blockExercises = [];

    $(blockContainer).find('.block-exercises tr').each(function() { // map doesn't work with json
        blockExercises.push({
            "exercise": $(this).find('.block-exercise :selected').data(),
            "quantity": $(this).find('.block-quantity').val()
        });
    });

    return blockExercises;
}




let exercises;

fetch('/api/exercises', {
    method: 'GET'
}).then(function(response) {
    if(response.ok) {
        response.json().then(data => {
            exercises = data;
        })
    } else {
        onError(response);
    }
});