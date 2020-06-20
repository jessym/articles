import React, { Component } from 'react';
import { IntlProvider } from 'react-intl';
import './App.css';
import Greeting from './Greeting';
import { loadTranslation } from './i18n';
import logo from './logo.svg';

class App extends Component {

  state = {
    locale: 'en-US',
    translation: undefined,
  };

  flushLocale = async (locale: string) => {
    const translation = await loadTranslation(locale);
    this.setState({ locale, translation });
  }

  componentDidMount() {
    const { locale } = this.state;
    this.flushLocale(locale);
  }

  render() {
    const { locale, translation } = this.state;
    if (!translation) {
      return <div>Loading...</div>;
    }
    return (
      <IntlProvider locale={locale} messages={translation}>
        <div className="App">
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <Greeting />
            <button onClick={() => this.flushLocale('en-US')}>English</button>
            <button onClick={() => this.flushLocale('es-ES')}>Spanish</button>
          </header>
        </div>
      </IntlProvider>
    );
  }
}

export default App;
