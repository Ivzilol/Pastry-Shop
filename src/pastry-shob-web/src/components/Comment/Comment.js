import React from 'react';

const Comment = (props) => {
    const {
        id,
        createdBy,
        text,
        emitDeleteComment,
        emitEditComment
    } = props;
    return (
        <div className="comments">
            <div className="comments-view-comment">
                <strong>{createdBy.username}:
                    <span
                        onClick={() => emitEditComment(id)}
                        className="comments-edit"
                    >
                        Edit
                    </span>
                    <span
                        onClick={() => emitDeleteComment(id)}
                        className="comments-delete"
                    >
                        Delete
                    </span>
                </strong>
                <p>{text}</p>
            </div>
        </div>
    );
};

export default Comment;