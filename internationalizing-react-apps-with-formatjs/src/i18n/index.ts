export function loadTranslation(locale: string): Promise<Record<string, string>> {
  if (locale === 'en-US') {
    return import('./enUS').then(module => module.default);
  }
  if (locale === 'es-ES') {
    return import('./esES').then(module => module.default);
  }
  throw new Error('Translation not found');
}