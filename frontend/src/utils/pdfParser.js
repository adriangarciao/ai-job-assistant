import { getDocument, GlobalWorkerOptions } from 'pdfjs-dist/legacy/build/pdf'
import workerUrl from 'pdfjs-dist/build/pdf.worker.mjs?url'

GlobalWorkerOptions.workerSrc = workerUrl

export async function parsePdf(arrayBuffer) {
  const loadingTask = getDocument({ data: arrayBuffer })
  const pdf = await loadingTask.promise
  let text = ''
  for (let i = 1; i <= pdf.numPages; i++) {
    const page = await pdf.getPage(i)
    const content = await page.getTextContent()
    const pageText = content.items.map((it) => (it.str || '')).join(' ')
    text += pageText + '\n'
  }
  return text
}

export default { parsePdf }
