import React from 'react';
import { FormattedMessage } from "react-intl";
import { TranslationKey } from './i18n/TranslationKey';

export default function Greeting() {
  return (
    <div>
      <FormattedMessage id={TranslationKey.greeting} />
    </div>
  );
}