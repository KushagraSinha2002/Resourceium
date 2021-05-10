/* Use this in any async context by invoking it as `await sleep(durationInMilliseconds)` */
export function sleep(time) {
  // eslint-disable-next-line no-console
  console.info(`Sleeping main thread for ${time} milliseconds`)
  return new Promise((resolve) => setTimeout(resolve, time))
}
