import {DropdownButton, Dropdown} from "react-bootstrap";
import * as i18 from "i18next";

const LanguagePicker = () => {

    const languageHandlerChange = (lang) => {
        i18.changeLanguage(lang);
        document.getElementById('selectedLanguage').textContent = lang;
        localStorage.setItem('selectedLanguage', lang);
    }

    return (
        <div className="language-picker">
            <DropdownButton title={<span id="selectedLanguage">Език</span>} className="language-picker--dropdown"
                            variant="warning">
                <Dropdown.Item onClick={() => languageHandlerChange('en')}>English</Dropdown.Item>
                <Dropdown.Item onClick={() => languageHandlerChange('bg')}>Български</Dropdown.Item>
            </DropdownButton>
        </div>
    )
}

export default LanguagePicker;