
import i18n from 'i18next'
import { initReactI18next } from 'react-i18next';

import enTranslation from './locales/en.json';
import bgTranslation from './locales/bg.json';

const I18n = () => {

    i18n
        .use(initReactI18next)
        .init({
            resources: {
                en: {
                    translation: enTranslation,
                },
                bg: {
                    translation: bgTranslation,
                },
            },
            lng: 'bg',
            fallbackLng: 'bg',
            interpolation: {
                escapeValue: false,
            },
        });
}

export default I18n;