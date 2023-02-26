import React from 'react';
import {Badge} from "react-bootstrap";

const StatusBadge = (props) => {
    const {text} = props;

    function getColorOfBadge() {
        if (text === "Working") {
            return "success"
        }
        return "danger";
    }
    return (
        <Badge className="badge-status"
            pill
            bg={getColorOfBadge()}
            style={{fontSize: "1em"}}>
            {text}
        </Badge>
    );
};

export default StatusBadge;