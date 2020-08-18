// For opening the note modal
function showNoteModal(noteId, noteTitle, noteDescription) {
    $('#note-id').val(noteId ? noteId : '');
    $('#note-title').val(noteTitle ? noteTitle : '');
    $('#note-description').val(noteDescription ? noteDescription : '');
    $('#noteModal').modal('show');
}

// For opening the credentials modal
function showCredentialModal(credentialId, url, username, password) {
    $('#credential-id').val(credentialId ? credentialId : '');
    $('#credential-url').val(url ? url : '');
    $('#credential-username').val(username ? username : '');
    $('#credential-password').val(password ? password : '');
    $('#credentialModal').modal('show');
}

function saveNote(ev) {
    ev.preventDefault();
    var note = {};
    note["title"] = $('#note-title').val();
    note["description"] = $('#note-description').val();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/note/add",
        data: JSON.stringify(note),
        dataType: 'json',
        success: function (data) {
            loadNoteList();
        },
        error: function (e) {
            console.log(e)
        }
    });
}

function deleteNote(noteId) {
    var note = {};
    note["id"] = noteId;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/note/delete",
        data: JSON.stringify(note),
        dataType: 'json',
        success: function (data) {
            loadNoteList();
        },
        error: function (e) {
            console.log(e)
        }
    });
}

function loadNoteList() {
    $.ajax({
        type: 'get',
        url: '/note/list',
        success: function (notesData) {
            $('#note_list').replaceWith(notesData);
            $('#noteModal').modal('hide');
        },
        error: function (e) {
            console.log(e)
        }
    })
}


