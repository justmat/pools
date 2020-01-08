Engine_Pools : CroneEngine {

	var <synth;

	*new { arg context, doneCallback;
		^super.new(context, doneCallback);
	}

	alloc {
		SynthDef(\synth, { |inL, inR, out, shimmer=0.00, verb=0.5, amp=0, freq=1000.0, res=0.1, gain=1.0, type=0.0, noiseLevel=0.0003,
		  t60=1.0, damp=0, size=1.0, diff=0.507, modDepth=0.1, modFreq=2.0, low=1.0, mid=1.0, high=1.0, lowcut=500.0, highcut=2000.0,
		  windowSize=0.5, pitchRatio=2.0, pitchDispersion=0.0, timeDispersion=0.02|
      
            var dry, wet, shifted, local, mix;
      
            dry = [In.ar(inL), In.ar(inR)];
            dry = DFM1.ar(dry, freq, res, gain, type, noiseLevel).softclip;

            local = LocalIn.ar(2);
      
            wet = JPverb.ar(dry + local, t60, damp, size, diff, modDepth, modFreq, low, mid, high, lowcut, highcut);
            shifted = PitchShift.ar(wet, windowSize, pitchRatio, pitchDispersion, timeDispersion);

            LocalOut.ar(shifted * shimmer);
            wet = LeakDC.ar(wet);
            mix = Mix.new([dry * amp, wet * verb]);
			
			Out.ar(out, mix);
		}).add;

		context.server.sync;

		synth = Synth.new(\synth, [
			\inL, context.in_b[0].index,			
			\inR, context.in_b[1].index,
			\out, context.out_b.index],
		context.xg);

		this.addCommand("amp", "f", {|msg|
			synth.set(\amp, msg[1]);
		});
		
		this.addCommand("verb", "f", {|msg|
			synth.set(\verb, msg[1]);
		});
		
		this.addCommand("shimmer", "f", {|msg|
		  synth.set(\shimmer, msg[1]);
		});
		
		this.addCommand("freq", "f", {|msg|
		  synth.set(\freq, msg[1]);
		});
		
		this.addCommand("res", "f", {|msg|
		  synth.set(\res, msg[1]);
		});
		
		this.addCommand("gain", "f", {|msg|
		  synth.set(\gain, msg[1]);
		});
		
		this.addCommand("type", "f", {|msg|
		  synth.set(\type, msg[1]);
		});
		
		this.addCommand("t60", "f", {|msg|
			synth.set(\t60, msg[1]);
		});
		
		this.addCommand("damp", "f", {|msg|
			synth.set(\damp, msg[1]);
		});
		
		this.addCommand("size", "f", {|msg|
			synth.set(\size, msg[1]);
		});
		
		this.addCommand("diff", "f", {|msg|
			synth.set(\diff, msg[1]);
		});
		
		this.addCommand("modDepth", "f", {|msg|
			synth.set(\modDepth, msg[1]);
		});
		
		this.addCommand("modFreq", "f", {|msg|
			synth.set(\modFreq, msg[1]);
		});
		
		this.addCommand("low", "f", {|msg|
			synth.set(\low, msg[1]);
		});
		
		this.addCommand("mid", "f", {|msg|
			synth.set(\mid, msg[1]);
		});
		
		this.addCommand("high", "f", {|msg|
			synth.set(\high, msg[1]);
		});
		
		this.addCommand("lowcut", "f", {|msg|
			synth.set(\lowcut, msg[1]);
		});
		
		this.addCommand("highcut", "f", {|msg|
			synth.set(\highcut, msg[1]);
		});
		
		this.addCommand("pitchRatio", "f", {|msg|
			synth.set(\pitchRatio, msg[1]);
		});
		
		this.addCommand("pitchDispersion", "f", {|msg|
			synth.set(\pitchDispersion, msg[1]);
		});
		
		this.addCommand("timeDispersion", "f", {|msg|
			synth.set(\timeDispersion, msg[1]);
		});
	}

	free {

            synth.free;
	}

} 
