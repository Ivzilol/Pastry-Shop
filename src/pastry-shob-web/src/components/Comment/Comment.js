import React from 'react';
import {useUser} from "../../UserProvider/UserProvider";
import jwt_decode from 'jwt-decode'

const Comment = (props) => {
    const user = useUser();
    const decodingJwt = jwt_decode(user.jwt);

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
                <strong>{createdBy}:
                    {
                        decodingJwt.sub === createdBy ?
                            <>
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
                    </span></>
                            :
                            <></>
                    }
                </strong>
                <p>{text}</p>
            </div>
        </div>
    );
};

export default Comment;