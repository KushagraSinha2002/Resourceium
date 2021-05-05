export function sleep(time) {
  // Use this in any async context by invoking it as `await sleep(durationInMilliseconds)`
  return new Promise((resolve) => setTimeout(resolve, time))
}
