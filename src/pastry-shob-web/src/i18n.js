
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import Backend from 'i18next-http-backend';

const lang = localStorage.getItem('lang');

i18n
    .use(Backend)
    .use(initReactI18next)
    .init({
        fallbackLng: lang === null ? 'bg' : `${lang}`,
        debug: true,
        interpolation: {
            escapeValue: false,
        }
    })
