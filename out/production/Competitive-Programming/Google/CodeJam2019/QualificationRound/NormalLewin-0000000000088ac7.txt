t = int(raw_input())

def gcd(a,b):
    if b == 0:
        return a
    return gcd(b, a%b)

def solve():
    n,l = map(int, raw_input().split())
    message = map(int, raw_input().split())
    primes = set()
    for i in xrange(l-1):
        if message[i] != message[i+1]:
            t = gcd(message[i], message[i+1])
            assert t <= n

            primes.add(t)
            primes.add(message[i]/t)
            primes.add(message[i+1]/t)

    vals = sorted(primes)
    d = {}
    idx = 0
    for v in vals:
        d[v] = idx
        idx += 1

    for i in xrange(26):
        test = [chr(d[vals[i]] + ord('A'))]
        pt = vals[i]
        ok = True
        for j in xrange(l):
            if message[j] % pt != 0:
                ok = False
                break
            pt = message[j] / pt
            test.append(chr(d[pt] + ord('A')))
        if ok:
            return ''.join(test)
    return "!!!"
    
for __ in xrange(t):
    print "Case #%d: %s" % (__+1, solve())