import React, { Component } from 'react';
import { FormattedMessage, IntlProvider } from 'react-intl';
import './App.css';
import { loadTranslation } from './i18n';
import { TranslationKey } from './i18n/TranslationKey';

class App extends Component {

  state = {
    locale: undefined,
    translation: undefined,
  }

  flushLocale = async (locale: string) => {
    const translation = await loadTranslation(locale);
    this.setState({ locale, translation });
  }

  componentDidMount() {
    const defaultLocale = 'en';
    this.flushLocale(defaultLocale);
  }

  render() {
    const { locale, translation } = this.state;
    if (!locale || !translation) {
      return <div>Loading...</div>;
    }
    return (
      <IntlProvider locale={locale!} messages={translation}>
        <div className="App">
          <header className="App-header">
            <button onClick={() => this.flushLocale('en')}>English</button>
            <button onClick={() => this.flushLocale('es')}>Spanish</button>
            <FormattedMessage id={TranslationKey.websiteGreeting} />
          </header>
        </div>
      </IntlProvider>
    );
  }
}

export default App;
