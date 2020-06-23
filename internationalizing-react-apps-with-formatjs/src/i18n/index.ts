export function loadTranslation(locale: string) {
  if (locale === 'en') {
    return import('./en').then(module => module.translation);
  }
  if (locale === 'es') {
    return import('./es').then(module => module.translation);
  }
  throw new Error('Translation not found');
}